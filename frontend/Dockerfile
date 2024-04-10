FROM node:latest
LABEL authors="wiktormalyska"

RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    qemu-user-static \
    binfmt-support

WORKDIR /app

COPY package.json .
RUN npm install

COPY . .

EXPOSE 8080

CMD ["npm", "run", "dev"]