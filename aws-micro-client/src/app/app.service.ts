import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { HttpClient, HttpResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';

 
export class Foo {
  constructor(
    public id: number,
    public name: string) { }
} 

@Injectable()
export class AppService {
  constructor(
    private _router: Router, private _http: HttpClient,private cookieService: CookieService){}
 
  obtainAccessToken(loginData){
    let params = new URLSearchParams();
    params.append('username',loginData.username);
    params.append('password',loginData.password);    
    params.append('grant_type','password');
    params.append('client_id','clientIdPassword');

    let headers = new HttpHeaders({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8', 'Authorization': 'Basic '+btoa("clientIdPassword:secret")});
    let options = { headers: new HttpHeaders({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8', 'Authorization': 'Basic '+btoa("clientIdPassword:secret")}),
    params : new HttpParams().set('username','admin').append('password',loginData.password).append('grant_type','password').append('client_id','clientIdPassword') };
    console.log(params.toString());
     this._http.post('http://localhost:9080/oauth/token',null, options)
    //.map(res => res.json())
    .subscribe(
      data => this.saveToken(data),
      err => alert('Invalid Credentials')
    ); 
  }


  saveToken(token){
    var expireDate = new Date().getTime() + (1000 * token.expires_in);
    this.cookieService.set("access_token", token.access_token, expireDate);
    console.log('Obtained Access token');
    this._router.navigate(['/']);
  }

  getResource(resourceUrl){
    var headers = new HttpHeaders({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8', 'Authorization': 'Bearer '+this.cookieService.get('access_token')});
    let options = { headers: headers};
    return this._http.get<Foo>(resourceUrl, options);
                   //.map((res:Response) => res.json())
                   //.catch((error:any) => Observable.throw(error.json().error || 'Server error'));
  }

  checkCredentials(){
    if (!this.cookieService.check('access_token')){
        this._router.navigate(['/login']);
    }
  } 

  logout() {
    this.cookieService.delete('access_token');
    this._router.navigate(['/login']);
  }
}