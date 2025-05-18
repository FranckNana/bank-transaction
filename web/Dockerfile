FROM node:18-alpine AS build

WORKDIR /usr/local/app

COPY ./ /usr/local/app/

RUN npm install -g @angular/cli

RUN npm install -f

RUN npm run build


FROM nginx:alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build /usr/local/app/dist/transaction-web/browser /usr/share/nginx/html

EXPOSE 8000