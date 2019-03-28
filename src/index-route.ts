import { Request, Response } from "express";
import { resolve } from "path";

export function indexRoute(req: Request, res: Response) {
  res.sendFile(resolve(__dirname, "../index.html"));
}
