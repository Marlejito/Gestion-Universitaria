import { useQuery } from "@tanstack/react-query";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { GraduationCap, Users, BookOpen, TrendingUp } from "lucide-react";
import { Skeleton } from "@/components/ui/skeleton";
import type { Student, Professor, Course, Enrollment } from "@shared/schema";

export default function Dashboard() {
  const { data: students, isLoading: studentsLoading } = useQuery<Student[]>({
    queryKey: ["/api/students"],
  });

  const { data: professors, isLoading: professorsLoading } = useQuery<Professor[]>({
    queryKey: ["/api/professors"],
  });

  const { data: courses, isLoading: coursesLoading } = useQuery<Course[]>({
    queryKey: ["/api/courses"],
  });

  const { data: enrollments, isLoading: enrollmentsLoading } = useQuery<Enrollment[]>({
    queryKey: ["/api/enrollments"],
  });

  const stats = [
    {
      title: "Total Estudiantes",
      value: students?.length || 0,
      icon: GraduationCap,
      color: "text-blue-600",
      bgColor: "bg-blue-100 dark:bg-blue-950",
      testId: "stat-students",
    },
    {
      title: "Total Profesores",
      value: professors?.length || 0,
      icon: Users,
      color: "text-green-600",
      bgColor: "bg-green-100 dark:bg-green-950",
      testId: "stat-professors",
    },
    {
      title: "Cursos Activos",
      value: courses?.filter((c) => c.status === "active").length || 0,
      icon: BookOpen,
      color: "text-purple-600",
      bgColor: "bg-purple-100 dark:bg-purple-950",
      testId: "stat-courses",
    },
    {
      title: "Total Inscripciones",
      value: enrollments?.length || 0,
      icon: TrendingUp,
      color: "text-orange-600",
      bgColor: "bg-orange-100 dark:bg-orange-950",
      testId: "stat-enrollments",
    },
  ];

  const isLoading = studentsLoading || professorsLoading || coursesLoading || enrollmentsLoading;

  return (
    <div className="p-6 space-y-6">
      <div>
        <h1 className="text-3xl font-semibold mb-2" data-testid="text-dashboard-title">
          Panel Principal
        </h1>
        <p className="text-muted-foreground">
          Sistema de Gestión Universitaria - Universidad TechFuture
        </p>
      </div>

      <div className="grid gap-6 grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
        {stats.map((stat) => (
          <Card key={stat.title}>
            <CardHeader className="flex flex-row items-center justify-between gap-2 space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">{stat.title}</CardTitle>
              <div className={`p-2 rounded-md ${stat.bgColor}`}>
                <stat.icon className={`h-4 w-4 ${stat.color}`} />
              </div>
            </CardHeader>
            <CardContent>
              {isLoading ? (
                <Skeleton className="h-8 w-16" />
              ) : (
                <div className="text-3xl font-bold" data-testid={stat.testId}>
                  {stat.value}
                </div>
              )}
            </CardContent>
          </Card>
        ))}
      </div>

      <div className="grid gap-6 grid-cols-1 lg:grid-cols-2">
        <Card>
          <CardHeader>
            <CardTitle>Actividad Reciente</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {enrollments?.slice(0, 5).map((enrollment) => (
                <div
                  key={enrollment.id}
                  className="flex items-center gap-4 text-sm"
                  data-testid={`activity-${enrollment.id}`}
                >
                  <div className="h-2 w-2 rounded-full bg-primary" />
                  <div className="flex-1">
                    <p className="text-sm">
                      Nueva inscripción en {enrollment.semester}
                    </p>
                    <p className="text-xs text-muted-foreground">
                      {new Date(enrollment.enrollmentDate).toLocaleDateString('es-ES')}
                    </p>
                  </div>
                </div>
              ))}
              {(!enrollments || enrollments.length === 0) && (
                <p className="text-sm text-muted-foreground text-center py-8">
                  Sin actividad reciente
                </p>
              )}
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Resumen del Sistema</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              <div className="flex items-center justify-between">
                <span className="text-sm text-muted-foreground">Estudiantes Activos</span>
                <span className="text-sm font-medium">
                  {students?.filter((s) => s.status === "active").length || 0}
                </span>
              </div>
              <div className="flex items-center justify-between">
                <span className="text-sm text-muted-foreground">Profesores Activos</span>
                <span className="text-sm font-medium">
                  {professors?.filter((p) => p.status === "active").length || 0}
                </span>
              </div>
              <div className="flex items-center justify-between">
                <span className="text-sm text-muted-foreground">Capacidad de Cursos</span>
                <span className="text-sm font-medium">
                  {enrollments?.length || 0} / {courses?.reduce((acc, c) => acc + c.capacity, 0) || 0}
                </span>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
