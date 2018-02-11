import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { LoadingModule } from 'ngx-loading';

// Services
import { UserService } from '../services/user.service';
import { AuthGuardService } from '../services/auth-guard.service';
import { SearchService } from '../services/search.service';

// Components
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { SearchComponent } from './search/search.component';
import { TorrentModalComponent } from './torrent-modal/torrent-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    SearchComponent,
    TorrentModalComponent
  ],
  entryComponents: [
    TorrentModalComponent
  ],
  imports: [
    LoadingModule,
    HttpModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgbModule.forRoot(),
    RouterModule.forRoot([
    {
      path: '',
      component: LoginComponent
    },
    {
      path: 'home',
      component: HomeComponent,
      canActivate: [AuthGuardService]
    },
    {
      path: 'search',
      component: SearchComponent,
      canActivate: [AuthGuardService]
    },
    {
      path: 'search/:value',
      component: SearchComponent,
      canActivate: [AuthGuardService]
    }
    ])
  ],
  providers: [
    UserService,
    SearchService,
    AuthGuardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
