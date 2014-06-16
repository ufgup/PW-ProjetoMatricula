/*create table curso (
	nome varchar(25),
	codigo int primary key
);
create table aluno (
	matricula varchar(6) primary key,
	nome varchar(50),
	idade int,
	sexo char,
	curso int 
); 

alter table aluno add foreign key (curso) references curso(codigo);


create table curso_log (
	codigo_curso int,
	data date,
	operacao varchar(20)
);

create table aluno_log (
	matricula_aluno varchar(6),
	data date,
	operacao varchar(20)
);

alter table curso_log add foreign key (codigo_curso) references curso(codigo);
alter table aluno_log add foreign key (matricula_aluno) references aluno(matricula);*/