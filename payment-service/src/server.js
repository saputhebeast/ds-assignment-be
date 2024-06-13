import express from "express";
import expressHealth from "express-health-middleware";
import context from "express-http-context";
import cors from "cors";
import helmet from "helmet";
import config from "./config";
import { errorHandler, resourceNotFoundHandler } from "./middleware";
import { connectMongo } from "./database";
import payment from "./module/payments/api/v1/controller";
import { authorization } from "./middleware/auth";

const initialize = () => {
  const app = express();

  app.use(express.json());

  app.use(express.urlencoded({ extended: true }));

  app.use(cors({
    origin: '*',
    methods: ['GET', 'POST', 'PUT', 'PATCH', 'DELETE', 'OPTIONS'],
    allowedHeaders: ['*'],
    exposedHeaders: ['*'],
    credentials: true,
  }));

  app.use(helmet());

  app.use("/system", expressHealth());

  app.use(context.middleware);

  app.use(authorization);

  connectMongo();

  app.use("/api/v1/payment", payment);

  app.use(resourceNotFoundHandler);

  app.use(errorHandler);

  global.__basedir = __dirname;

  app.listen(config.PORT, () => {
    console.log(`Payment service is listening on port ${config.PORT}`);
  });
};

initialize();
