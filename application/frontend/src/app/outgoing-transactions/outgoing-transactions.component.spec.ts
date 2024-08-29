import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OutgoingTransactionsComponent } from './outgoing-transactions.component';

describe('OutgoingTransactionsComponent', () => {
  let component: OutgoingTransactionsComponent;
  let fixture: ComponentFixture<OutgoingTransactionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OutgoingTransactionsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OutgoingTransactionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
