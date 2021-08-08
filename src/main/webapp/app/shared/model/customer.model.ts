export interface ICustomer {
  id?: number;
  name?: string;
  email?: string;
  phone?: string;
  address?: string;
}

export const defaultValue: Readonly<ICustomer> = {};
