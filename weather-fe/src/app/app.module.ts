import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule, MatIconModule, MatInputModule,
  MatListModule,
  MatProgressBarModule,
  MatProgressSpinnerModule
} from '@angular/material';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    NoopAnimationsModule,
    MatCardModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,
    FormsModule,
    MatProgressBarModule,
    MatListModule,
    MatIconModule,
    MatInputModule,
    MatButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
