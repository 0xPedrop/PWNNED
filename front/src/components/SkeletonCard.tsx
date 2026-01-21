import { Skeleton } from "@/components/ui/skeleton";

const SkeletonCard = () => {
  return (
    <div className="glass-card overflow-hidden">
      {/* Image skeleton */}
      <Skeleton className="h-40 w-full rounded-none" />

      {/* Content skeleton */}
      <div className="p-5 space-y-4">
        <Skeleton className="h-6 w-3/4" />
        <div className="space-y-2">
          <Skeleton className="h-4 w-full" />
          <Skeleton className="h-4 w-2/3" />
        </div>
        <div className="flex gap-4">
          <Skeleton className="h-4 w-20" />
          <Skeleton className="h-4 w-20" />
        </div>
        <Skeleton className="h-10 w-full" />
      </div>
    </div>
  );
};

export default SkeletonCard;
