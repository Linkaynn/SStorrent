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
import { AdminService } from '../services/admin.service';

// Components
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { SearchComponent } from './search/search.component';
import { TorrentModalComponent } from './torrent-modal/torrent-modal.component';
import { PreferencesComponent } from './preferences/preferences.component';
import { HelpComponent } from './help/help.component';
import { RegisterComponent } from './register/register.component';
import { RequestsComponent } from './requests/requests.component';
import { LogComponent } from './log/log.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    SearchComponent,
    TorrentModalComponent,
    PreferencesComponent,
    HelpComponent,
    RegisterComponent,
    RequestsComponent,
    LogComponent
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
      path: 'preferences',
      component: PreferencesComponent,
      canActivate: [AuthGuardService]
    },
    {
      path: 'help',
      component: HelpComponent,
      canActivate: [AuthGuardService]
    },
    {
      path: 'register',
      component: RegisterComponent
    },
    {
      path: 'requests',
      component: RequestsComponent
    },
    {
      path: 'logs',
      component: LogComponent
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
    AdminService,
    AuthGuardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
