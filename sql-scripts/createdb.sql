CREATE USER beermaster WITH PASSWORD 'beersecret';

DROP DATABASE IF EXISTS beernest;
CREATE DATABASE beernestdb;
GRANT ALL PRIVILEGES ON DATABASE beernestdb TO beermaster;
