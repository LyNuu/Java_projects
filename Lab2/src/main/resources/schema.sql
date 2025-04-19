CREATE TABLE owners (
  id integer PRIMARY KEY,
  name VARCHAR(30) NOT NULL,
  birthday VARCHAR(64)
);
CREATE TABLE pets (
  id integer PRIMARY KEY,
  name VARCHAR(64) NOT NULL,
  birthday VARCHAR(64),
  owner_id INTEGER references owners(id)
);
CREATE TABLE friends (
  id INTEGER primary key,
  name VARCHAR(64)
);

create table pet_friends(
pet_id integer references pets(id),
friend_id integer references friends(id),
primary key (pet_id, friend_id)
);