import React from 'react';
import { shallow } from 'enzyme';
import Routes from '../src/components/Routes';

describe('ViewContainer', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = shallow(<Routes />);
  });

  it('should be defined', () => {
    expect(wrapper).toBeDefined();
  });

  it('should be rednered', () => {
    expect(wrapper.find('div').length).toBeGreaterThan(0);
  });

  it('should have login route', () => {
    expect(wrapper.find({ pattern: '/login' }).length).toBe(1);
  });

  it('should have logout route', () => {
    expect(wrapper.find({ pattern: '/logout' }).length).toBe(1);
  });

  it('should have register route', () => {
    expect(wrapper.find({ pattern: '/register' }).length).toBe(1);
  });

  it('should have recover password route', () => {
    expect(wrapper.find({ pattern: '/recover-password' }).length).toBe(1);
  });

  it('should have update password route', () => {
    expect(wrapper.find({ pattern: '/update-password/:id' }).length).toBe(1);
  });

  it('should have generate widget route', () => {
    expect(wrapper.find({ pattern: '/generate-widget' }).length).toBe(1);
  });
  it('should have my account route', () => {
    expect(wrapper.find({ pattern: '/user/account' }).length).toBe(1);
  });

  it('should have my event route', () => {
    expect(wrapper.find({ pattern: '/user/event' }).length).toBe(1);
  });

  it('should have create event route', () => {
    expect(wrapper.find({ pattern: '/user/event/create' }).length).toBe(1);
  });

  it('should have view event route', () => {
    expect(wrapper.find({ pattern: '/user/event/view/:id' }).length).toBe(1);
  });

  it('should have members route', () => {
    expect(wrapper.find({ pattern: '/admin/members' }).length).toBe(1);
  });

  it('should have pending registrations route', () => {
    expect(wrapper.find({ pattern: '/admin/pending-registrations' }).length).toBe(1);
  });
});
