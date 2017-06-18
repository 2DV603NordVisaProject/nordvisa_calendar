import React from 'react';
import { shallow } from 'enzyme';
import MyAccountView from '../src/components/MyAccountView';
import UpdateAccount from '../src/components/UpdateAccount';
import UpdatePassword from '../src/components/UpdatePassword';
import PageTitle from '../src/components/PageTitle';
import en from '../src/i18n/en';

describe('MyAccountView', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<MyAccountView />, { context });
  });

  it('should be defined', () => {
    expect(wrapper).toBeDefined();
  });

  it('should be rendered', () => {
    expect(wrapper.find('div').length).toBeGreaterThan(0);
  });

  it('should contain a PageTitle-component', () => {
    expect(wrapper.find(PageTitle).length).toBe(1);
  });

  it('should contain the component <UpdateAccount/>', () => {
    expect(wrapper.contains(<UpdateAccount />)).toBe(true);
  });

  it('should contain the component <UpdateAccount/>', () => {
    expect(wrapper.contains(<UpdatePassword />)).toBe(true);
  });
});
