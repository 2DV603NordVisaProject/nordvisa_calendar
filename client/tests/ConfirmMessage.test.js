import { mount } from 'enzyme';
import React from 'react';
import ConfirmMessage from '../src/components/ConfirmMessage';
import Button from '../src/components/Button';

describe('ConfirmMessage', () => {
  let wrapper;
  const onClick = jest.fn();

  describe('prop "popup.pop" is false ', () => {
    const popup = { pop: false };
    beforeEach(() => {
      wrapper = mount(<ConfirmMessage popup={popup} onClick={onClick} />);
    });

    it('should be defined', () => {
      expect(wrapper).toBeDefined();
    });

    it('should  not render', () => {
      expect(wrapper.find('div').length).toBe(0);
    });
  });

  describe('prop "popup.pop" is true', () => {
    const popup = { pop: true };
    beforeEach(() => {
      wrapper = mount(<ConfirmMessage popup={popup} onClick={onClick} />);
    });

    it('should be defined', () => {
      expect(wrapper).toBeDefined();
    });

    it('should render', () => {
      expect(wrapper.find('div').length).toBeGreaterThan(0);
    });

    it('should have two Button-components', () => {
      expect(wrapper.find(Button).length).toBe(2);
    });

    it("should receive pop object as prop 'popup'", () => {
      const prop = wrapper.props().popup;
      expect(prop).toBeDefined();
    });

    it("should receive onClick func as prop 'onClick'", () => {
      const prop = wrapper.props().onClick;
      expect(prop).toBeDefined();
    });

    describe('user clicks no', () => {
      const btn = wrapper.find('.btn-error').first();

      beforeEach(() => {
        btn.simulate('click');
      });

      it("should update state 'pop' to false", () => {
        const prop = wrapper.state().pop;
        expect(prop).toBe(false);
      });
    });

    describe('user clicks yes', () => {
      const btn = wrapper.find('.btn-success').first();

      beforeEach(() => {
        btn.simulate('click');
      });

      it("should set 'pop' state to false", () => {
        const prop = wrapper.state().pop;
        expect(prop).toBe(false);
      });

      it("should call prop func. 'onClick'", () => {
        expect(onClick.mock.calls.length).toBe(1);
      });
    });
  });
});
