import React from 'react';
import { shallow } from 'enzyme';
import Logout from '../src/components/Logout';

describe('Logout', () => {
  let wrapper;
  const Client = {
    logout: jest.fn(),
  };

  beforeEach(() => {
    wrapper = shallow(<Logout />);
  });

  afterEach(() => {
    Client.logout.mockClear();
  });

  it('component should be defined', () => {
    expect(wrapper).toBeDefined();
  });


  it('should perform Logout', () => {
    expect(Client.logout).toHaveBeenCalled();
  });

  it('should redirect to login', () => {
    expect(wrapper.find({ to: '/login' }).length).toBe(1);
  });
});
