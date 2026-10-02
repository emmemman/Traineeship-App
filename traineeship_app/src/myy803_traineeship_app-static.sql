USE myy803_traineeship_app;

-- Κοινός κωδικός για ΟΛΟΥΣ: committee
SET @pwd = '$2a$12$1RHsG1ImXPPAyroJMnWXEuaOoSBssZ68BDTNRmoXnzkQizRBnf6vi';

-- =========================
-- (ΠΡΟΑΙΡΕΤΙΚΑ) Καθάρισμα
-- =========================
-- Προσοχή στη σειρά (FKs)
DELETE FROM evaluations;
UPDATE students SET position_id = NULL;
DELETE FROM traneeship_positions;
DELETE FROM students;
DELETE FROM professors;
DELETE FROM companies;
DELETE FROM users;

-- =========================
-- USERS
-- =========================
INSERT INTO users (username, password, role) VALUES
                                                 ('committee',       @pwd, 'COMMITTEE'),

                                                 ('techcorp',        @pwd, 'COMPANY'),
                                                 ('softsolutions',   @pwd, 'COMPANY'),
                                                 ('datadynamics',    @pwd, 'COMPANY'),

                                                 ('prof_john',       @pwd, 'PROFESSOR'),
                                                 ('prof_maria',      @pwd, 'PROFESSOR'),
                                                 ('prof_george',     @pwd, 'PROFESSOR'),
                                                 ('prof_eleni',      @pwd, 'PROFESSOR'),

                                                 ('alice',           @pwd, 'STUDENT'),
                                                 ('bob',             @pwd, 'STUDENT'),
                                                 ('chris',           @pwd, 'STUDENT'),
                                                 ('diana',           @pwd, 'STUDENT'),
                                                 ('eva',             @pwd, 'STUDENT'),
                                                 ('frank',           @pwd, 'STUDENT'),
                                                 ('gina',            @pwd, 'STUDENT'),
                                                 ('harry',           @pwd, 'STUDENT');

-- =========================
-- COMPANIES
-- =========================
INSERT INTO companies (username, company_name, company_location) VALUES
                                                                     ('techcorp',      'TechCorp Ltd',        'Athens'),
                                                                     ('softsolutions', 'Soft Solutions SA',   'Thessaloniki'),
                                                                     ('datadynamics',  'DataDynamics PC',     'Heraklion');

-- =========================
-- PROFESSORS
-- =========================
INSERT INTO professors (username, professor_name, interests) VALUES
                                                                 ('prof_john',   'John Papadopoulos',   'databases,backend,software engineering'),
                                                                 ('prof_maria',  'Maria Nikolaou',      'networks,security,cloud'),
                                                                 ('prof_george', 'George Andreou',      'ai,data science,ml'),
                                                                 ('prof_eleni',  'Eleni Markou',        'web,ui,frontend');

-- =========================
-- STUDENTS
-- =========================
INSERT INTO students
(username, student_name, am, average_grade, preferred_location, interests, skills, looking_for_traineeship, logbook)
VALUES
    ('alice', 'Alice Georgiou', 1001, 7.6, 'Athens',        'databases,backend',     'java,spring,mysql',          1, NULL),
    ('bob',   'Bob Nikolaou',   1002, 6.9, 'Thessaloniki',  'frontend,ui',           'html,css,javascript',        1, NULL),
    ('chris', 'Chris Ioannou',  1003, 8.1, 'Athens',        'cloud,security',        'linux,networking,cloud',     1, NULL),
    ('diana', 'Diana Petrou',   1004, 7.2, 'Heraklion',     'data science,ai',       'python,ml,pandas',           1, NULL),
    ('eva',   'Eva Sarri',      1005, 8.4, 'Heraklion',     'databases,data',        'sql,python,etl',             1, NULL),
    ('frank', 'Frank Dimos',    1006, 6.5, 'Athens',        'web,frontend',          'react,javascript,css',       1, NULL),
    ('gina',  'Gina Kosta',     1007, 7.9, 'Thessaloniki',  'networks,security',     'networking,security,linux',  1, NULL),
    ('harry', 'Harry Mavros',   1008, 6.8, 'Athens',        'backend,cloud',         'java,docker,sql',            1, NULL);

-- =========================
-- TRAINEESHIP POSITIONS
-- =========================
INSERT INTO traneeship_positions
(id, title, description, from_date, to_date, topics, skills, is_assigned, student_log_book, pass_fail, company_username, professor_username)
VALUES
    (1,  'Backend Developer Intern',     'Spring Boot backend development',           '2026-02-01', '2026-06-30',
     'backend,databases',                'java,spring,mysql',          1, NULL, NULL, 'techcorp',      'prof_john'),

    (2,  'Frontend Developer Intern',    'Frontend web development',                   '2026-03-01', '2026-07-31',
     'frontend,ui',                      'html,css,javascript',        1, NULL, NULL, 'softsolutions', 'prof_eleni'),

    (3,  'Cloud & Security Intern',      'Cloud ops and security basics',              '2026-02-15', '2026-06-15',
     'cloud,security',                   'linux,cloud,networking',     1, NULL, NULL, 'techcorp',      'prof_maria'),

    (4,  'Data Science Intern',          'Intro ML pipeline & reporting',              '2026-03-10', '2026-07-10',
     'ai,data science',                  'python,ml,pandas',            1, NULL, NULL, 'datadynamics',  'prof_george'),

    (5,  'SQL & ETL Intern',             'ETL tasks and SQL reporting',                 '2026-04-01', '2026-08-01',
     'databases,data',                   'sql,python,etl',              1, NULL, NULL, 'datadynamics',  'prof_john'),

    (6,  'DevOps Intern',                'Docker, CI basics, deployments',             '2026-03-01', '2026-07-01',
     'cloud,backend',                    'docker,linux,sql',            0, NULL, NULL, 'techcorp',      NULL),

    (7,  'UI/UX Assistant Intern',       'UI audit and small improvements',            '2026-02-20', '2026-06-20',
     'ui,web',                           'figma,html,css',              0, NULL, NULL, 'softsolutions', NULL),

    (8,  'Network Monitoring Intern',    'Monitoring dashboards & alerts',             '2026-03-05', '2026-07-05',
     'networks,security',                'networking,linux,security',   0, NULL, NULL, 'techcorp',      NULL),

    (9,  'Java Backend Intern',          'REST APIs + database integration',           '2026-04-10', '2026-08-10',
     'backend,databases',                'java,sql,spring',             0, NULL, NULL, 'softsolutions', NULL),

    (10, 'Data Analytics Intern',        'Analytics and data cleaning',                '2026-04-15', '2026-08-15',
     'data,ai',                          'python,sql,pandas',           0, NULL, NULL, 'datadynamics',  NULL);

-- =========================
-- ASSIGN students -> positions (one-to-one via students.position_id)
-- =========================
UPDATE students SET position_id = 1  WHERE username = 'alice';
UPDATE students SET position_id = 2  WHERE username = 'bob';
UPDATE students SET position_id = 3  WHERE username = 'chris';
UPDATE students SET position_id = 4  WHERE username = 'diana';
UPDATE students SET position_id = 5  WHERE username = 'eva';

-- 3 students μένουν διαθέσιμοι (χωρίς assigned position): frank, gina, harry

-- =========================
-- EVALUATIONS (2 positions με εταιρεία+καθηγητή)
-- =========================

INSERT INTO evaluations
(company_facilities, company_guidance, effectiveness, efficiency, evaluation_type, motivation, position_id)
VALUES
    (5, 5, 4, 4, 'COMPANY_EVALUATION', 4, 1),
    (NULL, NULL, 5, 5, 'PROFESSOR_EVALUATION', 5, 1);
