import { Request, Response } from "express";

export function helloWorld(req: Request, res: Response) {
  res.status(200).send("Hello, World!");
}
