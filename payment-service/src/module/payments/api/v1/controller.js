import { celebrate, Segments } from "celebrate";
import { traced, tracedAsyncHandler } from "@sliit-foss/functions";
import express from "express";
import { response } from "../../../../utils";
import { initializePaymentSchema } from "./schema";
import { serviceInitializePayment, serviceRetrievePayment } from "./service";

const payment = express.Router();

payment.post(
  '/',
  celebrate({ [Segments.BODY]: initializePaymentSchema }),
  tracedAsyncHandler(async function controllerInitiatePayment(req, res) {
    const data = await traced(serviceInitializePayment)(req.body);
    return response({ res, status: 201, message: "Payment initialized", data: data });
  }),
);

payment.get(
  '/:id',
  tracedAsyncHandler(async function controllerRetrievePayment(req, res) {
    const data = await traced(serviceRetrievePayment)(req.params.id);
    return response({ res, status: 200, message: 'Payment retrieved successfully', data: data });
  }),
);

export default payment;
