FROM node:20
WORKDIR /app

COPY package.json .
RUN npm install
RUN npm install -g serve -y

COPY . .
RUN npm run build

EXPOSE 80

CMD ["npx", "serve","-s","/app/dist","-l","80"]