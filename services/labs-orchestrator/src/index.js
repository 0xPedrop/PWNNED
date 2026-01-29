const express = require('express');
const k8s = require('@kubernetes/client-node');
const crypto = require('crypto');

const app = express();
app.use(express.json());

const kc = new k8s.KubeConfig();
// Carrega a config automaticamente (funciona local com KUBECONFIG ou na nuvem via ServiceAccount)
kc.loadFromDefault();

const k8sApi = kc.makeApiClient(k8s.CoreV1Api);
const k8sAppsApi = kc.makeApiClient(k8s.AppsV1Api);
const k8sNetworkingApi = kc.makeApiClient(k8s.NetworkingV1Api);

const NAMESPACE = process.env.NAMESPACE || 'pwnned-labs';
const BASE_DOMAIN = process.env.BASE_DOMAIN || 'labs.pwnned.tech';

const imageMapById = {
    "1": "pedropaulodel/lab-xss-reflected:latest",
    "101": "pedropaulodel/lab-sqli-basics:latest"
};

app.post('/spawn', async (req, res) => {
    const { labId, userId } = req.body;
    const selectedImage = imageMapById[labId];

    if (!selectedImage) {
        return res.status(400).json({ error: 'ID de laboratório inválido' });
    }

    const sessionHash = crypto.randomBytes(4).toString('hex');
    const labName = `lab-${sessionHash}`;

    try {
        // 1. Deployment
        await k8sAppsApi.createNamespacedDeployment(NAMESPACE, {
            metadata: { name: labName, labels: { app: labName } },
            spec: {
                replicas: 1,
                selector: { matchLabels: { app: labName } },
                template: {
                    metadata: { labels: { app: labName } },
                    spec: {
                        containers: [{
                            name: 'lab-container',
                            image: selectedImage,
                            ports: [{ containerPort: 80 }]
                        }]
                    }
                }
            }
        });

        // 2. Service
        await k8sApi.createNamespacedService(NAMESPACE, {
            metadata: { name: labName },
            spec: {
                selector: { app: labName },
                ports: [{ port: 80, targetPort: 80 }]
            }
        });

        // 3. Ingress 
        await k8sNetworkingApi.createNamespacedIngress(NAMESPACE, {
            metadata: {
                name: labName,
                annotations: {
                    "nginx.ingress.kubernetes.io/rewrite-target": "/",
                    "nginx.ingress.kubernetes.io/proxy-body-size": "8m"
                }
            },
            spec: {
                ingressClassName: 'nginx', // Essencial para o DOKS reconhecer a rota
                rules: [{
                    host: `${sessionHash}.${BASE_DOMAIN}`,
                    http: {
                        paths: [{
                            path: '/',
                            pathType: 'Prefix',
                            backend: {
                                service: { name: labName, port: { number: 80 } }
                            }
                        }]
                    }
                }]
            }
        });

        res.json({
            url: `http://${sessionHash}.${BASE_DOMAIN}`,
            expiresIn: '10m'
        });

    } catch (err) {
        console.error('Erro ao subir lab:', err.body || err);
        res.status(500).json({ error: 'Falha na orquestração do cluster' });
    }
});

const PORT = process.env.PORT || 3001;
app.listen(PORT, () => console.log(`Orchestrator rodando na porta ${PORT}`));