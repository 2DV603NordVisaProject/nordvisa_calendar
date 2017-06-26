import React from 'react';
import { mount, shallow } from 'enzyme';
import Button from '../src/components/Button';

describe('Button', () => {
  let wrapper;
  const onClick = jest.fn();

  describe('prop "form" is true', () => {
    beforeEach(() => {
      wrapper = shallow(<Button theme="error" form>login</Button>);
    });

    it('should be defined', () => {
      expect(wrapper).toBeDefined();
    });

    it('should render', () => {
      expect(wrapper.find('input').length).toBe(1);
    });
    it('prop children should be "login"', () => {
      expect(wrapper.props().value).toBe('login');
    });

    it('class should be set to error', () => {
      expect(wrapper.find('.error').length).toBe(1);
    });
  });

  describe('props are default', () => {
    beforeEach(() => {
      wrapper = mount(<Button onClick={onClick} />);
    });

    it('should be defined', () => {
      expect(wrapper).toBeDefined();
    });

    it('should render', () => {
      expect(wrapper.find('button').length).toBe(1);
    });

    it('theme prop should be set', () => {
      expect(wrapper.props().theme).toBeDefined();
    });

    it('children prop should be set', () => {
      expect(wrapper.props().children).toBeDefined();
    });

    it('onClick prop should be set', () => {
      expect(wrapper.props().onClick).toBeDefined();
    });

    describe('user clicks the button', () => {
      wrapper = mount(<Button onClick={onClick} />);
      const btn = wrapper.find('button').first();

      beforeEach(() => {
        btn.simulate('click', {});
      });

      it('onClick method should be called', () => {
        expect(onClick.mock.calls.length).toBe(1);
      });
    });
  });
});
