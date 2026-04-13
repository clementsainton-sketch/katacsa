import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListExpensesTableComponent } from './list-expenses-table.component';

describe('ListExpensesTableComponent', () => {
  let component: ListExpensesTableComponent;
  let fixture: ComponentFixture<ListExpensesTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListExpensesTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListExpensesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
