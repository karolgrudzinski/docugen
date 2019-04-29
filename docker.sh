#!/bin/bash

sudo docker start docugen-psql || sudo docker run --name docugen-psql -p 5432:5432 -e POSTGRES_DB=docugen -e POSTGRES_PASSWORD=docugensecret -d postgres
