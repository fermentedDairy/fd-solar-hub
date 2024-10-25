create extension if not exists timescaledb;

create table config (
                        name text primary key,
                        value text
);

create table solar_data (
                           id uuid,
                           time timestamptz not null default now(),
                           topic text not null,
                           value text not null
);

select public.create_hypertable('solar_data', 'time');