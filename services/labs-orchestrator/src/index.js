// PWNNED/services/labs-orchestrator/src/index.js
const express = require('express');
const k8s = require('@kubernetes/client-node');
const crypto = require('crypto');

const app = express();
app.use(express.json());

const kc = new k8s.KubeConfig();
kc.loadFromDefault();

const k8sAppsApi = kc.makeApiClient(k8s.AppsV1Api);
const k8sApi = kc.makeApiClient(k8s.CoreV1Api);
const k8sNetworkingApi = kc.makeApiClient(k8s.NetworkingV1Api);

const NAMESPACE = process.env.NAMESPACE || 'pwnned-labs';
const BASE_DOMAIN = process.env.BASE_DOMAIN || 'labs.pwnned.tech';

const FIXED_IMAGE = "pedropaulodel/lab-xss-reflected:latest";

app.post('/spawn', async (req, res) => {
    // Recebe o ID só pra logar, mas não usa pra decidir a imagem
    const { labId, userId } = req.body;
    
    console.log(`Recebido pedido para Lab ID: ${labId}. Forçando lab da Prefeitura.`);

    // Gera hash único
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
                            image: FIXED_IMAGE, // <--- USA SEMPRE A SUA IMAGEM
                            ports: [{ containerPort: 80 }] // Seu server.js roda na 80
                        }]
                    }
                }
            }
        });

        // 2. Service (Porta 80 -> 80)
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
                    "nginx.ingress.kubernetes.io/proxy-body-size": "8m"
                }
            },
            spec: {
                ingressClassName: 'nginx', 
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
        res.status(500).json({ error: 'Falha na orquestração' });
    }
});

const PORT = process.env.PORT || 3001;
app.listen(PORT, () => console.log(`Orchestrator rodando na porta ${PORT}`));