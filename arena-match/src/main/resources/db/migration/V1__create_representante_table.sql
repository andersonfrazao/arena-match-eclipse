create table if not exists representante (
    id bigint primary key,
    cpf varchar(11) not null,
    email varchar(200) not null,
    nome varchar(200) not null,
    senha_hash varchar(200) not null,
    ativo boolean not null default true,
    data_fim_trial date not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
    );

create unique index if not exists uk_representante_cpf on representante (cpf);
create unique index if not exists uk_representante_email on representante (lower(email));
