# MMU_UniversityManager
Quello che vogliamo creare è un programma che consenta la gestione di una parte delle 
componenti di una università. In particolare, considereremo le seguenti componenti:
• Professore;
• Studente;
• Corso;
le cui informazioni sono reperibili attraverso un 
database
. Il programma utilizzerà anche 
un 
servizio per spedire email
.
Il programma che andiamo a scrivere dovrà permettere di effettuare le seguenti azioni:
• Assegnare un professore ad un corso;
• Assegnare uno studente ad un corso;
• Assegnare un professore come tutor ad uno studente.
Vediamo ora in dettaglio quali sono le entità presenti sul nostro database e le relazioni 
che sussistono fra di esse.
Entità e relazioni
Lo schema relazionale del nostro database prevede sei entità:
• 
Student
: ID, Nome, Cognome, Mail, IDTutor;
• 
Teacher
: ID, Nome, Cognome, Mail;
• 
Course
: ID, Nome, Mail, IDTeacher;
• 
CourseRequest
: IDStudent, IDCourse;
• 
TutorRequest
: IDStudent, IDTeacher.
• 
CourseAttendance
: IDStudent, IDCourse;
Le entità CourseRequest, TutorRequest, e CourseAttendance sono utilizzate per 
modellare le relazioni molti a molti che legano le altre tre entità.
Tra queste entità sussistono le seguenti relazioni:
• 
Student
– Uno Student può essere iscritto a zero o più Course;
– Uno Student può avere al massimo un Teacher come tutor;
– Uno Student può avere zero o più CourseRequest in corso;
– Uno Student può avere al massimo una TutorRequest in corso;
– Uno Student può essere iscritto a zero o più Course (CourseAttendance);
• 
Teacher
– Un Teacher può tenere zero o più Course;
– Un Teacher può fare da tutor ad al massimo tre Student;
– Un Teacher può avere zero o più TutorRequest in corso;
• 
Course
– Un Course può essere frequentato da zero o più Student;
– Un Course deve essere tenuto da esattamente un Teacher;
– Un Course può avere zero o più CourseRequest in corso;
– Un Course può avere zero o più Student iscritti ad esso (CourseAttendance);
• 
CourseRequest
– Una CourseRequest può essere effettuata da un solo Student;
– Una CourseRequest può essere riferita ad un solo Course;
• 
TutorRequest
– Una TutorRequest può essere effettuata da un solo Student;
– Una TutorRequest può essere riferita ad un solo Teacher;
• 
CourseAttendance
– Una CourseAttendance può essere riferita ad un solo Student;
– Una CourseAttendance può essere riferita ad un solo Course;
Funzionalità Richieste
Ora che abbiamo visto in dettaglio quali siano le componenti con cui andremo a lavorare,
vediamo quali siano le funzionalità che queste dovranno offrire:
• 
Student
:
– Invio di una richiesa di tutoraggio ad un Teacher, con la condizione che non si abbia già 
un tutor o una richiesta in corso, e successivo invio di una mail di avviso;
– Invio di una richiesta di iscrizione ad un Course, con la condizione che non si abbia già 
una richiesta in corso;
– Invio di una mail al proprio tutor;
• 
Teacher
:
– Gestione di una richiesta di tutoraggio, con la condizione che si sia tutor di meno di 3 
Student, e successivo invio di una mail di conferma;
– Invio di una mail a tutti gli Student di cui si è tutor;
• 
Course
:
– Assegnazione di un Teacher al Course, rimuovendo quello precedente, e successivo 
invio di una mail di avviso ad entrambi;
– Gestione di una richiesta di iscrizione e successivo invio di una mail di conferma;
Le tre entità CourseRequest, TutorRequest, e CourseAttendance serviranno solamente a
mantenere i relativi attributi. Inoltre, semplificheremo alcune operazioni: per esempio, 
non assegneremo alla mail un corpo, ma solamente gli indirizzi del mittente e del 
destinatario.
