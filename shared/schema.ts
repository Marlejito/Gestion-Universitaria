import { sql } from "drizzle-orm";
import { sqliteTable, text, integer, real } from "drizzle-orm/sqlite-core";
import { createInsertSchema } from "drizzle-zod";
import { z } from "zod";

// Students table
export const students = sqliteTable("students", {
  id: text("id").primaryKey().$defaultFn(() => crypto.randomUUID()),
  studentId: text("student_id").notNull().unique(),
  firstName: text("first_name").notNull(),
  lastName: text("last_name").notNull(),
  email: text("email").notNull().unique(),
  phone: text("phone"),
  program: text("program").notNull(),
  semester: integer("semester").notNull(),
  status: text("status").notNull().default("active"),
  enrollmentDate: integer("enrollment_date", { mode: "timestamp" }).notNull().default(sql`(unixepoch())`),
});

export const insertStudentSchema = createInsertSchema(students).omit({
  id: true,
  enrollmentDate: true,
});

export type InsertStudent = z.infer<typeof insertStudentSchema>;
export type Student = typeof students.$inferSelect;

// Professors table
export const professors = sqliteTable("professors", {
  id: text("id").primaryKey().$defaultFn(() => crypto.randomUUID()),
  professorId: text("professor_id").notNull().unique(),
  firstName: text("first_name").notNull(),
  lastName: text("last_name").notNull(),
  email: text("email").notNull().unique(),
  phone: text("phone"),
  department: text("department").notNull(),
  specialization: text("specialization"),
  status: text("status").notNull().default("active"),
  hireDate: integer("hire_date", { mode: "timestamp" }).notNull().default(sql`(unixepoch())`),
});

export const insertProfessorSchema = createInsertSchema(professors).omit({
  id: true,
  hireDate: true,
});

export type InsertProfessor = z.infer<typeof insertProfessorSchema>;
export type Professor = typeof professors.$inferSelect;

// Courses table
export const courses = sqliteTable("courses", {
  id: text("id").primaryKey().$defaultFn(() => crypto.randomUUID()),
  courseCode: text("course_code").notNull().unique(),
  name: text("name").notNull(),
  description: text("description"),
  department: text("department").notNull(),
  credits: integer("credits").notNull(),
  capacity: integer("capacity").notNull(),
  professorId: text("professor_id").references(() => professors.id),
  semester: text("semester").notNull(),
  schedule: text("schedule"),
  room: text("room"),
  prerequisites: text("prerequisites", { mode: "json" }).$type<string[]>(),
  status: text("status").notNull().default("active"),
});

export const insertCourseSchema = createInsertSchema(courses).omit({
  id: true,
});

export type InsertCourse = z.infer<typeof insertCourseSchema>;
export type Course = typeof courses.$inferSelect;

// Enrollments table
export const enrollments = sqliteTable("enrollments", {
  id: text("id").primaryKey().$defaultFn(() => crypto.randomUUID()),
  studentId: text("student_id").notNull().references(() => students.id),
  courseId: text("course_id").notNull().references(() => courses.id),
  semester: text("semester").notNull(),
  status: text("status").notNull().default("enrolled"),
  enrollmentDate: integer("enrollment_date", { mode: "timestamp" }).notNull().default(sql`(unixepoch())`),
});

export const insertEnrollmentSchema = createInsertSchema(enrollments).omit({
  id: true,
  enrollmentDate: true,
});

export type InsertEnrollment = z.infer<typeof insertEnrollmentSchema>;
export type Enrollment = typeof enrollments.$inferSelect;

// Grades table
export const grades = sqliteTable("grades", {
  id: text("id").primaryKey().$defaultFn(() => crypto.randomUUID()),
  enrollmentId: text("enrollment_id").notNull().references(() => enrollments.id),
  grade: real("grade"),
  letterGrade: text("letter_grade"),
  status: text("status").notNull().default("pending"),
  gradedDate: integer("graded_date", { mode: "timestamp" }),
});

export const insertGradeSchema = createInsertSchema(grades).omit({
  id: true,
  gradedDate: true,
});

export type InsertGrade = z.infer<typeof insertGradeSchema>;
export type Grade = typeof grades.$inferSelect;

// Extended types for joined queries
export type EnrollmentWithDetails = Enrollment & {
  student: Student;
  course: Course;
  grade?: Grade;
};

export type CourseWithDetails = Course & {
  professor?: Professor;
  enrollmentCount?: number;
};

export type StudentWithStats = Student & {
  enrolledCourses?: number;
  gpa?: number;
};
