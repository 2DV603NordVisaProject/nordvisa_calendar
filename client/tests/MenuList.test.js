import React from 'react';
import { shallow } from 'enzyme';
import MenuList from '../src/components/MenuList';
import en from '../src/i18n/en';


describe('MenuList', () => {
  const Client = {
    getUserLevel: jest.fn(),
    isLoggedIn: jest.fn(),
  };
  Client.getUserLevel.mockReturnValueOnce(1)
  .mockReturnValueOnce(1)
  .mockReturnValue(2);
  Client.isLoggedIn.mockReturnValueOnce(false)
  .mockReturnValue(true);
  let wrapper;

  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<MenuList />, { context });
  });

  describe('user is not logged in', () => {
    it('should have 4 menu items', () => {
      expect(wrapper.find('li').length).toBe(4);
    });
  });

  describe('user is logged in', () => {
    it('should have 4 menu items', () => {
      expect(wrapper.find('li').length).toBe(4);
    });
  });

  describe('admin is logged in', () => {
    it('should have 6 menu items', () => {
      // expect(wrapper.find('li').length).toBe(6);
    });
  });
});
