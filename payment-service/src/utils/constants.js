import createError from "http-errors";

export const errors = {
  missing_token: createError(401, "Bearer token is missing")
};
