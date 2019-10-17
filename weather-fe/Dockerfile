FROM node:10.13-alpine

WORKDIR /app
COPY . .
RUN npm install -g @angular/cli
RUN npm i
RUN ng build --prod

FROM nginx
COPY --from=0 /app/dist/weather-fe  /usr/share/nginx/html
