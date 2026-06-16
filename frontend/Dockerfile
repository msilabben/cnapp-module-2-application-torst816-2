# syntax=docker/dockerfile:1

FROM dhi.io/node:20-sfw-dev AS builder

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm ci

COPY . .

FROM dhi.io/node:20 AS runtime

WORKDIR /app

ENV NODE_ENV=production

COPY --from=builder --chown=10001:10001 /app /app

USER 10001:10001

EXPOSE 3000

CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0", "--port", "3000"]