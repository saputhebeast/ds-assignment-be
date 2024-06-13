import context from "express-http-context";

export const authorization = (req, res, next) => {
    if (req.headers.authorization && req.headers.authorization.startsWith('Bearer ')) {
        const token = req.headers.authorization.split(' ')[1];
        context.set("user", token);
    } else {
        console.log('No Authorization Bearer Token found');
    }
    next();
};
