import dayjs from 'dayjs';
import { IProductType } from 'app/shared/model/product-type.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { weightTypes } from 'app/shared/model/enumerations/weight-types.model';
import { paymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export interface ICustomerBought {
  id?: number;
  weightType?: weightTypes;
  unitPrice?: number;
  totalPrice?: number;
  deliveryDate?: string;
  remarks?: string | null;
  status?: paymentStatus;
  totalWeight?: number;
  productType?: IProductType;
  customer?: ICustomer;
}

export const defaultValue: Readonly<ICustomerBought> = {};
