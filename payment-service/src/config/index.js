import { Joi } from "celebrate";
import dotenv from "dotenv";

dotenv.config();

class Base {
  static get schema() {
    return {
      PORT: Joi.number().optional(),
      DB_URL: Joi.string().required(),
      JWT_SECRET: Joi.string().required(),
      STRIPE_SECRET_KEY: Joi.string().required(),
    };
  }
  static get values() {
    return {
      PORT: process.env.PORT ?? 3000,
      DB_URL: process.env.DB_URL ?? "mongodb+srv://admin:2lgiIC4opKrZ2jVR@cluster0.vzb0mdl.mongodb.net/payment-service",
      JWT_SECRET: process.env.JWT_SECRET ?? "413F4428472B4B6250655368566D5970337336763979244226452948404D6351",
      STRIPE_SECRET_KEY: process.env.STRIPE_SECRET ?? "sk_test_51Nci2pFIZ3GgqNz2q1cnfKavejqTm9W6OpTPsNj3qI8ObAzf2oWeDvm4j2wn0sYMkV35xnwSqc0VqRuwVIzTWtZg00yQ6zcY0q"
    };
  }
}

const config = Base.values;

const { error } = Joi.object(Base.schema).validate(config);

if (error) {
  console.error(`Environment validation failed. \nDetails - ${error.details[0].message}\nExiting...`);
  process.exit(1);
}

export default config;
