export const response = ({ res, status, message, data }) => {
  const payload = {
    data,
    message
  };
  return res.status(status).json(payload);
};
