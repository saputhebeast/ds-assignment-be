import { traced } from '@sliit-foss/functions';
import { createPaymentIntent, retrievePaymentIntent } from '../../../../services';

export const serviceInitializePayment = (payment) => {
  return traced(createPaymentIntent)({
    amount: Number((payment.amount * 100).toFixed(0)),
    currency: 'lkr',
    description: `Order Payment`,
    metadata: payment.metadata,
    shipping: {
      name: payment.customer.name,
      address: {
        line1: payment.customer.address ?? 'Mock Address',
        postal_code: '21030',
        city: 'Colombo',
        state: 'CA',
        country: 'LK',
      },
    }
  });
};

export const serviceRetrievePayment = (id) => {
  return traced(retrievePaymentIntent)(id);
};
