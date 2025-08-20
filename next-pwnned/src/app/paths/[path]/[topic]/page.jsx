// Importações
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import PathsLayout from "@/app/components/pathsComponents/layouts/PathsLayout";
import pathsData from "@/app/data/pathsData";
import { topicsData } from "@/app/data/topicsData";
import stylesLayout from "@/app/components/pathsComponents/layouts/PathsLayout.module.css";
import { redirect } from "next/navigation";

export default async function TopicPage({ params }) {
  const session = await getServerSession(authOptions);

  const { path, topic } = params;

  if (!session) {
    redirect("/login");
  }

  const currentPathData = pathsData[path];
  const currentTopicData = currentPathData?.topics?.[topic];
  const currentPathTopics = topicsData[path] || [];

  let nextTopicUrl = null;
  const currentIndex = currentPathTopics.findIndex(
    (item) => item.slug === topic
  );
  if (currentIndex < currentPathTopics.length - 1) {
    const nextTopicSlug = currentPathTopics[currentIndex + 1].slug;
    nextTopicUrl = `/paths/${path}/${nextTopicSlug}`;
  }

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
      <div>
        {currentTopicData.content.map((block, index) => {
          switch (block.type) {
            case "h2":
              return <h2 key={index}>{block.text}</h2>;
            case "p":
              return (
                <p key={index} className={stylesLayout.contentParagraph}>
                  {block.text}
                </p>
              );
            case "code":
              return (
                <pre key={index} className={stylesLayout.codeBlock}>
                  <code>{block.text}</code>
                </pre>
              );
            default:
              return null;
          }
        })}
      </div>
    </PathsLayout>
  );
}
