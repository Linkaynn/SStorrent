import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TorrentModalComponent } from './torrent-modal.component';

describe('TorrentModalComponent', () => {
  let component: TorrentModalComponent;
  let fixture: ComponentFixture<TorrentModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TorrentModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TorrentModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
