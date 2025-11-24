import { useState } from "react";
import { useQuery, useMutation } from "@tanstack/react-query";
import { Plus, Search, Trash2, CheckCircle, XCircle } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  insertEnrollmentSchema,
  type Enrollment,
  type InsertEnrollment,
  type Student,
  type Course
} from "@shared/schema";
import { Skeleton } from "@/components/ui/skeleton";
import { Badge } from "@/components/ui/badge";
import { useToast } from "@/hooks/use-toast";
import { apiRequest, queryClient } from "@/lib/queryClient";

export default function Enrollments() {
  const [searchQuery, setSearchQuery] = useState("");
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const { toast } = useToast();

  const { data: enrollments, isLoading } = useQuery<Enrollment[]>({
    queryKey: ["/api/enrollments"],
  });

  const { data: students } = useQuery<Student[]>({
    queryKey: ["/api/students"],
  });

  const { data: courses } = useQuery<Course[]>({
    queryKey: ["/api/courses"],
  });

  const form = useForm<InsertEnrollment>({
    resolver: zodResolver(insertEnrollmentSchema),
    defaultValues: {
      studentId: "",
      courseId: "",
      semester: "",
      status: "enrolled",
    },
  });

  const createMutation = useMutation({
    mutationFn: (data: InsertEnrollment) => apiRequest("POST", "/api/enrollments", data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["/api/enrollments"] });
      setIsDialogOpen(false);
      form.reset();
      toast({
        title: "Éxito",
        description: "Inscripción creada exitosamente",
      });
    },
    onError: (error: Error) => {
      toast({
        title: "Error",
        description: error.message,
        variant: "destructive",
      });
    },
  });

  const deleteMutation = useMutation({
    mutationFn: (id: string) => apiRequest("DELETE", `/api/enrollments/${id}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["/api/enrollments"] });
      toast({
        title: "Éxito",
        description: "Inscripción eliminada exitosamente",
      });
    },
  });

  const getStudentName = (studentId: string) => {
    const student = students?.find((s) => s.id === studentId);
    return student ? `${student.firstName} ${student.lastName}` : "Desconocido";
  };

  const getCourseName = (courseId: string) => {
    const course = courses?.find((c) => c.id === courseId);
    return course ? course.name : "Desconocido";
  };

  const getCourseCode = (courseId: string) => {
    const course = courses?.find((c) => c.id === courseId);
    return course ? course.courseCode : "N/A";
  };

  const filteredEnrollments = enrollments?.filter((enrollment) => {
    const studentName = getStudentName(enrollment.studentId).toLowerCase();
    const courseName = getCourseName(enrollment.courseId).toLowerCase();
    const courseCode = getCourseCode(enrollment.courseId).toLowerCase();
    const query = searchQuery.toLowerCase();

    return studentName.includes(query) ||
      courseName.includes(query) ||
      courseCode.includes(query) ||
      enrollment.semester.toLowerCase().includes(query);
  });

  const onSubmit = (data: InsertEnrollment) => {
    createMutation.mutate(data);
  };

  const semesters = ["Otoño 2024", "Primavera 2025", "Verano 2025", "Otoño 2025"];

  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center justify-between flex-wrap gap-4">
        <div>
          <h1 className="text-3xl font-semibold mb-2" data-testid="text-enrollments-title">
            Inscripciones
          </h1>
          <p className="text-muted-foreground">
            Gestionar inscripciones de estudiantes en cursos
          </p>
        </div>
        <Button onClick={() => setIsDialogOpen(true)} data-testid="button-create-enrollment">
          <Plus className="h-4 w-4 mr-2" />
          Nueva Inscripción
        </Button>
      </div>

      <Card>
        <CardHeader>
          <div className="flex items-center gap-4">
            <div className="relative flex-1">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
              <Input
                placeholder="Buscar por estudiante, curso o semestre..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="pl-10"
                data-testid="input-search-enrollments"
              />
            </div>
          </div>
        </CardHeader>
        <CardContent>
          {isLoading ? (
            <div className="space-y-4">
              {[1, 2, 3].map((i) => (
                <Skeleton key={i} className="h-16 w-full" />
              ))}
            </div>
          ) : (
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Estudiante</TableHead>
                  <TableHead>Curso</TableHead>
                  <TableHead>Código del Curso</TableHead>
                  <TableHead>Semestre</TableHead>
                  <TableHead>Estado</TableHead>
                  <TableHead>Fecha de Inscripción</TableHead>
                  <TableHead className="text-right">Acciones</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {filteredEnrollments?.map((enrollment) => (
                  <TableRow key={enrollment.id} data-testid={`row-enrollment-${enrollment.id}`}>
                    <TableCell className="font-medium">
                      {getStudentName(enrollment.studentId)}
                    </TableCell>
                    <TableCell>{getCourseName(enrollment.courseId)}</TableCell>
                    <TableCell>
                      <code className="text-xs font-mono bg-muted px-2 py-1 rounded">
                        {getCourseCode(enrollment.courseId)}
                      </code>
                    </TableCell>
                    <TableCell>{enrollment.semester}</TableCell>
                    <TableCell>
                      <Badge
                        variant={enrollment.status === "enrolled" ? "default" : "secondary"}
                        className="gap-1"
                      >
                        {enrollment.status === "enrolled" ? (
                          <CheckCircle className="h-3 w-3" />
                        ) : (
                          <XCircle className="h-3 w-3" />
                        )}
                        {enrollment.status}
                      </Badge>
                    </TableCell>
                    <TableCell className="text-sm text-muted-foreground">
                      {new Date(enrollment.enrollmentDate).toLocaleDateString()}
                    </TableCell>
                    <TableCell className="text-right">
                      <Button
                        variant="ghost"
                        size="icon"
                        onClick={() => deleteMutation.mutate(enrollment.id)}
                        data-testid={`button-delete-${enrollment.id}`}
                      >
                        <Trash2 className="h-4 w-4 text-destructive" />
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
                {(!filteredEnrollments || filteredEnrollments.length === 0) && (
                  <TableRow>
                    <TableCell colSpan={7} className="text-center py-8 text-muted-foreground">
                      No se encontraron inscripciones
                    </TableCell>
                  </TableRow>
                )}
              </TableBody>
            </Table>
          )}
        </CardContent>
      </Card>

      <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
        <DialogContent className="max-w-md">
          <DialogHeader>
            <DialogTitle>Nueva Inscripción</DialogTitle>
            <DialogDescription>
              Inscribir un estudiante en un curso
            </DialogDescription>
          </DialogHeader>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
              <FormField
                control={form.control}
                name="studentId"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Estudiante</FormLabel>
                    <Select onValueChange={field.onChange} defaultValue={field.value}>
                      <FormControl>
                        <SelectTrigger data-testid="select-student">
                          <SelectValue placeholder="Seleccionar estudiante" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {students?.map((student) => (
                          <SelectItem key={student.id} value={student.id}>
                            {student.firstName} {student.lastName} ({student.studentId})
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="courseId"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Curso</FormLabel>
                    <Select onValueChange={field.onChange} defaultValue={field.value}>
                      <FormControl>
                        <SelectTrigger data-testid="select-course">
                          <SelectValue placeholder="Seleccionar curso" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {courses?.map((course) => (
                          <SelectItem key={course.id} value={course.id}>
                            {course.courseCode} - {course.name}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="semester"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Semestre</FormLabel>
                    <Select onValueChange={field.onChange} defaultValue={field.value}>
                      <FormControl>
                        <SelectTrigger data-testid="select-semester">
                          <SelectValue placeholder="Seleccionar semestre" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {semesters.map((sem) => (
                          <SelectItem key={sem} value={sem}>
                            {sem}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="status"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Estado</FormLabel>
                    <Select onValueChange={field.onChange} defaultValue={field.value}>
                      <FormControl>
                        <SelectTrigger data-testid="select-status">
                          <SelectValue placeholder="Seleccionar estado" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        <SelectItem value="enrolled">Inscrito</SelectItem>
                        <SelectItem value="withdrawn">Retirado</SelectItem>
                        <SelectItem value="completed">Completado</SelectItem>
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <DialogFooter>
                <Button
                  type="button"
                  variant="outline"
                  onClick={() => setIsDialogOpen(false)}
                  data-testid="button-cancel"
                >
                  Cancelar
                </Button>
                <Button
                  type="submit"
                  disabled={createMutation.isPending}
                  data-testid="button-submit-enrollment"
                >
                  {createMutation.isPending ? "Creando..." : "Crear Inscripción"}
                </Button>
              </DialogFooter>
            </form>
          </Form>
        </DialogContent>
      </Dialog>
    </div>
  );
}
