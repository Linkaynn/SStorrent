export class Token {
  public token : string;
  public expires : number;

  constructor(token: string, expires: number) {
    this.token = token;
    this.expires = expires;
  }

  public hasExpired() : boolean {
    return new Date().getTime() >= this.expires;
  }
}
