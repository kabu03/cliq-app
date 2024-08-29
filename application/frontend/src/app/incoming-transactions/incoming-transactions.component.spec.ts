import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IncomingTransactionsComponent } from './incoming-transactions.component';

describe('IncomingTransactionsComponent', () => {
  let component: IncomingTransactionsComponent;
  let fixture: ComponentFixture<IncomingTransactionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IncomingTransactionsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IncomingTransactionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
