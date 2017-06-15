import React from 'react';
import { shallow } from 'enzyme';
import Member from '../src/components/Member';
import en from '../src/i18n/en';

describe('Members', () => {
  let wrapper;
  const context = { language: en };
  const member = {
    email: 'sample@gmail.com',
    role: 'USER',
    id: 1,
  };
  const onChange = jest.fn();

  beforeEach(() => {
    wrapper = shallow(<Member onChange={onChange} member={member} />, { context });
  });

  it('should render component container', () => {
    expect(wrapper.find('li').length).toBe(1);
  });

  it('should render member id', () => {
    const select = wrapper.find('select').first();
    expect(select.props().name).toEqual(member.id);
  });

  it('should render email', () => {
    const emailEl = wrapper.find('.email-cell p').first();
    expect(emailEl.text()).toBe(member.email);
  });

  it('should render value', () => {
    const select = wrapper.find('select').first();
    expect(select.props().defaultValue).toBe(member.role);
  });

  describe('member is user', () => {
    const member = {
      email: 'sample@gmail.com',
      role: 'USER',
      id: 1,
    };

    beforeEach(() => {
      wrapper = shallow(<Member onChange={onChange} member={member} />, { context });
    });

    it('admin option should not be disabled', () => {
      expect(wrapper.find('option').at(1).props().disabled).toBe(false);
    });

    it('user option should not be disabled', () => {
      expect(wrapper.find('option').at(0).props().disabled).toBe(false);
    });

    it('delete option should not be disabled', () => {
      expect(wrapper.find('option').at(3).props().disabled).toBe(false);
    });
  });

  describe('member is super_admin', () => {
    const member = {
      email: 'sample@gmail.com',
      role: 'SUPER_ADMIN',
      id: 1,
    };
    beforeEach(() => {
      wrapper = shallow(<Member onChange={onChange} member={member} />, { context });
    });

    it('admin option should be disabled', () => {
      expect(wrapper.find('option').at(1).props().disabled).toBe(true);
    });

    it('user option should be disabled', () => {
      expect(wrapper.find('option').at(0).props().disabled).toBe(true);
    });

    it('delete option should be disabled', () => {
      expect(wrapper.find('option').at(3).props().disabled).toBe(true);
    });
  });

  describe('user change option access level dropdown', () => {
    const value = 'ADMIN';

    beforeEach(() => {
      const select = wrapper.find('select').first();
      select.simulate('change', {
        target: { value },
      });
    });

    it('should send value onChange', () => {
      const invocationArgs = onChange.mock.calls[0];
      expect(invocationArgs[0].target.value).toBe(value);
    });
  });
});
