create table if not exists meeting
(
    id bigserial not null
        constraint meeting_pkey
            primary key,
    coordinatex double precision,
    coordinatey double precision,
    date timestamp,
    description varchar(255),
    name varchar(255)
);


create table if not exists owner
(
    id bigserial not null
        constraint owner_pkey
            primary key,
    date_of_birth timestamp,
    login varchar(255),
    password varchar(255),
    photo_path varchar(255),
    full_name varchar(255),
    email varchar(255),
    activation_code varchar(255),
    active boolean
);


create table if not exists dog
(
    id bigserial not null
        constraint dog_pkey
            primary key,
    breed varchar(255),
    date_of_birth timestamp,
    name varchar(255),
    photo_path varchar(255),
    sex boolean,
    size varchar(255),
    owner_id bigint
        constraint fkoin0hsi5etp79v758jc3oc840
            references owner
);


create table if not exists meeting_owner
(
    user_id bigint not null
        constraint fkajv3t4dy460gf01ncixyr2hqg
            references owner,
    meeting_id bigint not null
        constraint fk9t1ggk665yin26x9xcvanr24v
            references meeting
);


create table if not exists message
(
    id bigserial not null
        constraint message_pkey
            primary key,
    date timestamp,
    message_text varchar(255),
    meeting_id bigint
        constraint fkk6bn8lk5fhwyhheh1w2fg9exx
            references meeting,
    sender_id bigint
        constraint fke5veslwtdlfgs8qqciq04im4l
            references owner
);


create table if not exists place
(
    id bigserial not null
        constraint place_pkey
            primary key,
    coordinatex double precision,
    coordinatey double precision,
    description varchar(255),
    name varchar(255),
    photo_path varchar(255),
    type varchar(255),
    creator_id bigint
        constraint fkre4alavidjtdkmdu18f96vm74
            references owner
);


create table if not exists question
(
    id bigserial not null
        constraint question_pkey
            primary key,
    date timestamp,
    question_text varchar(255),
    author_id bigint
        constraint fk7iod60qbxhjri7xsmn5eogelj
            references owner
);


create table if not exists answer
(
    id bigserial not null
        constraint answer_pkey
            primary key,
    date timestamp,
    answer_text varchar(255),
    author_id bigint
        constraint fkh02mtt91etp673ga3dtxijy5g
            references owner,
    question_id bigint
        constraint fk8frr4bcabmmeyyu60qt7iiblo
            references question
);


create table if not exists walking_area
(
    id bigserial not null
        constraint walking_area_pkey
            primary key,
    coordinatex double precision,
    coordinatey double precision,
    description varchar(255),
    name varchar(255),
    photo_path varchar(255),
    creator_id bigint
        constraint fk6fpitsgmnc8g6e2vlskqrjixm
            references owner
);


