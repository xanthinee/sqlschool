
DROP TABLE IF EXISTS public."GROUPS";
CREATE TABLE IF NOT EXISTS public."GROUPS"
(
    group_id integer NOT NULL,
    group_name character(6) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "GROUPS_pkey" PRIMARY KEY (group_id)
);

ALTER TABLE IF EXISTS public."GROUPS"
    OWNER to postgres;

DROP TABLE IF EXISTS public."STUDENTS";
CREATE TABLE IF NOT EXISTS public."STUDENTS"
(
    student_id integer NOT NULL,
    group_id integer,
    first_name character(255) COLLATE pg_catalog."default" NOT NULL,
    second_name character(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "STUDENTS_pkey" PRIMARY KEY (student_id)
);

ALTER TABLE IF EXISTS public."STUDENTS"
    OWNER to postgres;

DROP TABLE IF EXISTS public."COURSES";
CREATE TABLE IF NOT EXISTS public."COURSES"
(
    course_id integer NOT NULL,
    course_name character(255) COLLATE pg_catalog."default" NOT NULL,
    course_description character(510) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "COURSES_pkey" PRIMARY KEY (course_id)
);

ALTER TABLE IF EXISTS public."COURSES"
    OWNER to postgres;