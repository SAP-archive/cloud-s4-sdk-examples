import { Destination, executeHttpRequest, HttpMethod } from "@sap/cloud-sdk-core";
import { Request, Response } from "express";

export async function helloWorld(req: Request, res: Response) {
  await doSomething({
    url: "http://example.com"
  }).then((greeting: string) => {
    res.status(200).send(`${greeting}, World!`);
  });
}

export function doSomething(destination: Destination) {
  return executeHttpRequest(destination, {
    method: HttpMethod.GET
  })
  .then(() => "Hello")
  .catch(() => "Bye");
}
