CREATE TABLE clients (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         address VARCHAR(255),
                         phone VARCHAR(20),
                         isProfessional BOOLEAN NOT NULL
);
CREATE TYPE project_status AS ENUM ('INPROGRESS', 'FINISHED', 'CANCELLED');

CREATE TABLE projects (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          margeBenifit DOUBLE PRECISION NOT NULL,
                          coutTotal DOUBLE PRECISION NOT NULL,

                          projectStatus project_status NOT NULL,
                          client_id INTEGER REFERENCES clients(id) ON DELETE CASCADE
);

create table composants (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            composantType VARCHAR(255) NOT NULL,
                            tauxTva DOUBLE PRECISION NOT NULL,
                            project_id INTEGER REFERENCES projects(id) ON DELETE CASCADE
);

create table materials(

                          coutUnitaire DOUBLE PRECISION,
                          quantite DOUBLE PRECISION,
                          coutTransport DOUBLE PRECISION,
                          coefficientQualite DOUBLE PRECISION

)inherits (composants);

create table main_douvre(
                            typeOuvrier VARCHAR(255),
                            tauxHoraire DOUBLE PRECISION,
                            heuresTravail DOUBLE PRECISION,
                            productiviteOuvrier DOUBLE PRECISION

)inherits (composants);

create table devis(
                      id SERIAL PRIMARY KEY,
                      project_id INT,
                      montantEstime DOUBLE PRECISION NOT NULL,
                      dateEmission DATE NOT NULL,
                      dateValidite DATE NOT NULL,
                      isAccept BOOLEAN NOT NULL,
                      FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);