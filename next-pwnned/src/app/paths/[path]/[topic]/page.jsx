"use client";

import { useParams, useRouter } from "next/navigation";
import PathsLayout from "@/app/components/pathsComponents/layouts/PathsLayout.js";
import pathsData from "@/app/data/pathsData"; // Ainda necessário para o conteúdo principal
import stylesLayout from "@/app/components/pathsComponents/layouts/PathsLayout.module.css";
import { topicsData } from "@/app/data/topicsData"; // Importe o arquivo central de tópicos
import { useEffect, useState } from "react";

console.log(pathsData);
console.log(topicsData);

export default function TopicPage() {
  const { path, topic } = useParams();
  const router = useRouter();
  const [nextTopicUrl, setNextTopicUrl] = useState(null);

  console.log("Path:", path);
  console.log("Topic:", topic);
  console.log("pathsData[path]:", pathsData[path]);
  const currentPathData = pathsData[path];
  const currentTopicData = currentPathData?.topics?.[topic];
  console.log("currentTopicData:", currentTopicData);

  // Obtém os tópicos para o path atual do arquivo central
  const currentPathTopics = topicsData[path] || [];
  console.log("currentPathTopics:", currentPathTopics);

  useEffect(() => {
    const currentIndex = currentPathTopics.findIndex(
      (item) => item.slug === topic
    );
    if (currentIndex < currentPathTopics.length - 1) {
      const nextTopicSlug = currentPathTopics[currentIndex + 1].slug;
      setNextTopicUrl(`/paths/${path}/${nextTopicSlug}`);
    } else {
      setNextTopicUrl(null); // Ou alguma outra URL se for o último tópico
    }
  }, [path, topic, currentPathTopics]);

  if (!currentTopicData) {
    return (
      <PathsLayout>
        <h1>Conteúdo não encontrado</h1>
      </PathsLayout>
    );
  }

  const progressSteps = currentPathTopics.map((item) => {
    let status = "incomplete";
    if (item.slug === topic) {
      status = "doing";
    } else {
      const currentTopicIndex = currentPathTopics.findIndex(
        (t) => t.slug === topic
      );
      const itemIndex = currentPathTopics.findIndex(
        (t) => t.slug === item.slug
      );
      if (itemIndex < currentTopicIndex) {
        status = "complete";
      }
    }
    return { ...item, status };
  });

  return (
    <PathsLayout
      currentPath={path}
      progressSteps={progressSteps}
      nextTopicUrl={nextTopicUrl}
    >
      <h1 className={stylesLayout.contentTitle}>{currentTopicData.title}</h1>
      <p>{currentTopicData.content}</p>
    </PathsLayout>
  );
}
