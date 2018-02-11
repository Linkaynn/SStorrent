import { Token } from "./token";

export class User {
  public name : string;
  public token : Token;

  public mirrors : string[] = [];

  constructor(name: string, token) {
    this.name = name;
    this.token = new Token(token.token, parseInt(token.expires));
  }

  public setMirrors(mirrors) {
    this.mirrors = mirrors;
  }
}
