import { mount } from 'enzyme';
import React from 'react';
import ConfirmMessage from '../src/components/ConfirmMessage';
import Button from '../src/components/Button';

describe('ConfirmMessage', () => {
  let wrapper;
  const onYesClick = jest.fn();
  const onNoClick = jest.fn();

  describe('prop "popup.pop" is true', () => {
    const popup = { msg: 'hello world' };
    beforeEach(() => {
      wrapper = mount(<ConfirmMessage
        popup={popup}
        onYesClick={onYesClick}
        onNoClick={onNoClick}
      />,
    );
    });

    it('should be defined', () => {
      expect(wrapper).toBeDefined();
    });

    it('should render', () => {
      expect(wrapper.find('div').length).toBeGreaterThan(0);
    });

    it('should display the text from prop popup.msg', () => {
      const childText = wrapper.find('p').first().text();
      expect(childText).toBe(popup.msg);
    });

    it('should have two Button-components', () => {
      expect(wrapper.find(Button).length).toBe(2);
    });

    it("should receive pop object as prop 'popup'", () => {
      const prop = wrapper.props().popup;
      expect(prop).toBeDefined();
    });

    it('onNoClick prop should be defined', () => {
      const prop = wrapper.props().onNoClick;
      expect(prop).toBeDefined();
    });

    it('onYesClick prop should be defined', () => {
      const prop = wrapper.props().onYesClick;
      expect(prop).toBeDefined();
    });
  });
});
