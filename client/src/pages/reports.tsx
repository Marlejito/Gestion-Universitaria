import { useQuery } from "@tanstack/react-query";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { BarChart, FileText, TrendingUp, Users } from "lucide-react";
import { Skeleton } from "@/components/ui/skeleton";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";
import type { Student, Professor, Course, Enrollment, Grade } from "@shared/schema";

export default function Reports() {
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

  const { data: grades } = useQuery<Grade[]>({
    queryKey: ["/api/grades"],
  });

  const isLoading = studentsLoading || professorsLoading || coursesLoading || enrollmentsLoading;

  // Calculate statistics
  const totalEnrollments = enrollments?.length || 0;
  const activeEnrollments = enrollments?.filter((e) => e.status === "enrolled").length || 0;
  const completedEnrollments = enrollments?.filter((e) => e.status === "completed").length || 0;

  // Calculate average GPA
  // Sistema de calificación colombiano (1.0-5.0)
  const calculateColombianGrade = (grade: number): number => {
    if (grade >= 90) return 5.0;
    if (grade >= 80) return 4.5;
    if (grade >= 70) return 4.0;
    if (grade >= 60) return 3.0;
    return 1.0 + (grade / 60) * 2.0;
  };

  const avgGrade =
    grades && grades.length > 0
      ? (
        grades.reduce((sum, g) => sum + calculateColombianGrade(parseFloat(String(g.grade || "0"))), 0) /
        grades.length
      ).toFixed(1)
      : "0.0";

  // Department enrollment statistics
  const departmentStats = courses?.reduce((acc, course) => {
    const courseEnrollments = enrollments?.filter((e) => e.courseId === course.id).length || 0;
    if (!acc[course.department]) {
      acc[course.department] = { courses: 0, enrollments: 0 };
    }
    acc[course.department].courses += 1;
    acc[course.department].enrollments += courseEnrollments;
    return acc;
  }, {} as Record<string, { courses: number; enrollments: number }>);

  // Top enrolled courses
  const courseEnrollmentCounts = courses?.map((course) => ({
    course,
    count: enrollments?.filter((e) => e.courseId === course.id).length || 0,
  }));

  const topCourses = courseEnrollmentCounts
    ?.sort((a, b) => b.count - a.count)
    .slice(0, 5);

  return (
    <div className="p-6 space-y-6">
      <div>
        <h1 className="text-3xl font-semibold mb-2" data-testid="text-reports-title">
          Reportes y Analítica
        </h1>
        <p className="text-muted-foreground">
          Estadísticas del sistema y analítica de inscripciones
        </p>
      </div>

      <div className="grid gap-6 grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between gap-2 space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Inscripciones</CardTitle>
            <div className="p-2 rounded-md bg-blue-100 dark:bg-blue-950">
              <Users className="h-4 w-4 text-blue-600" />
            </div>
          </CardHeader>
          <CardContent>
            {isLoading ? (
              <Skeleton className="h-8 w-16" />
            ) : (
              <div className="text-3xl font-bold" data-testid="stat-total-enrollments">
                {totalEnrollments}
              </div>
            )}
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between gap-2 space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Inscripciones Activas</CardTitle>
            <div className="p-2 rounded-md bg-green-100 dark:bg-green-950">
              <TrendingUp className="h-4 w-4 text-green-600" />
            </div>
          </CardHeader>
          <CardContent>
            {isLoading ? (
              <Skeleton className="h-8 w-16" />
            ) : (
              <div className="text-3xl font-bold" data-testid="stat-active-enrollments">
                {activeEnrollments}
              </div>
            )}
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between gap-2 space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Completadas</CardTitle>
            <div className="p-2 rounded-md bg-purple-100 dark:bg-purple-950">
              <FileText className="h-4 w-4 text-purple-600" />
            </div>
          </CardHeader>
          <CardContent>
            {isLoading ? (
              <Skeleton className="h-8 w-16" />
            ) : (
              <div className="text-3xl font-bold" data-testid="stat-completed-enrollments">
                {completedEnrollments}
              </div>
            )}
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between gap-2 space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Promedio (Escala 1.0-5.0)</CardTitle>
            <div className="p-2 rounded-md bg-orange-100 dark:bg-orange-950">
              <BarChart className="h-4 w-4 text-orange-600" />
            </div>
          </CardHeader>
          <CardContent>
            {isLoading ? (
              <Skeleton className="h-8 w-16" />
            ) : (
              <div className="text-3xl font-bold" data-testid="stat-avg-gpa">
                {avgGrade}
              </div>
            )}
          </CardContent>
        </Card>
      </div>

      <div className="grid gap-6 grid-cols-1 lg:grid-cols-2">
        <Card>
          <CardHeader>
            <CardTitle>Inscripciones por Departamento</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Departamento</TableHead>
                  <TableHead className="text-right">Cursos</TableHead>
                  <TableHead className="text-right">Inscripciones</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {departmentStats &&
                  Object.entries(departmentStats).map(([dept, stats]) => (
                    <TableRow key={dept} data-testid={`dept-${dept}`}>
                      <TableCell className="font-medium">{dept}</TableCell>
                      <TableCell className="text-right">{stats.courses}</TableCell>
                      <TableCell className="text-right">
                        <Badge variant="outline">{stats.enrollments}</Badge>
                      </TableCell>
                    </TableRow>
                  ))}
                {!departmentStats && (
                  <TableRow>
                    <TableCell colSpan={3} className="text-center py-8 text-muted-foreground">
                      No hay datos disponibles
                    </TableCell>
                  </TableRow>
                )}
              </TableBody>
            </Table>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Cursos Más Inscritos</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Curso</TableHead>
                  <TableHead>Código</TableHead>
                  <TableHead className="text-right">Inscripciones</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {topCourses?.map(({ course, count }) => (
                  <TableRow key={course.id} data-testid={`top-course-${course.id}`}>
                    <TableCell className="font-medium">{course.name}</TableCell>
                    <TableCell>
                      <code className="text-xs font-mono bg-muted px-2 py-1 rounded">
                        {course.courseCode}
                      </code>
                    </TableCell>
                    <TableCell className="text-right">
                      <Badge>{count}</Badge>
                    </TableCell>
                  </TableRow>
                ))}
                {(!topCourses || topCourses.length === 0) && (
                  <TableRow>
                    <TableCell colSpan={3} className="text-center py-8 text-muted-foreground">
                      No hay datos de inscripciones disponibles
                    </TableCell>
                  </TableRow>
                )}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Distribución de Calificaciones</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-5 gap-4">
            {[
              { letter: "A", grade: "5.0", min: 4.5 },
              { letter: "B", grade: "4.5", min: 4.0 },
              { letter: "C", grade: "4.0", min: 3.0 },
              { letter: "D", grade: "3.0", min: 2.0 },
              { letter: "F", grade: "1.0", min: 0 }
            ].map(({ letter, grade, min }) => {
              const count = grades?.filter((g) => {
                const gradeValue = parseFloat(String(g.grade || "0"));
                if (letter === "A") return gradeValue >= 4.5;
                if (letter === "B") return gradeValue >= 4.0 && gradeValue < 4.5;
                if (letter === "C") return gradeValue >= 3.0 && gradeValue < 4.0;
                if (letter === "D") return gradeValue >= 2.0 && gradeValue < 3.0;
                return gradeValue < 2.0;
              }).length || 0;
              const percentage =
                grades && grades.length > 0 ? ((count / grades.length) * 100).toFixed(1) : "0.0";
              return (
                <div
                  key={letter}
                  className="flex flex-col items-center gap-2 p-4 rounded-md border"
                  data-testid={`grade-dist-${letter}`}
                >
                  <div className="text-3xl font-bold">{letter}</div>
                  <div className="text-sm text-muted-foreground">{grade}</div>
                  <div className="text-2xl font-semibold text-muted-foreground">{count}</div>
                  <div className="text-xs text-muted-foreground">{percentage}%</div>
                </div>
              );
            })}
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
