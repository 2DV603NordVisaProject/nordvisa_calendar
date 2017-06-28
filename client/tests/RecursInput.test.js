import React from 'react';
import { shallow } from 'enzyme';
import en from '../src/i18n/en';
import RecursInput from '../src/components/RecursInput';

describe('RecursInput', () => {
  let wrapper;
  const context = { language: en };
  const fields = {};
  const onInputChange = jest.fn();

  beforeEach(() => {
    wrapper = shallow(<RecursInput fields={fields} onInputChange={onInputChange} />, { context });
  });

  afterEach(() => {
    onInputChange.mockClear();
  });

  it('should be defined', () => {
    expect(wrapper).toBeDefined();
  });

  it('should render', () => {
    expect(wrapper.find('div').length).toBeGreaterThan(0);
  });

  it('should render 1 input', () => {
    expect(wrapper.find('input').length).toBe(1);
  });

  it('should not render any select element', () => {
    expect(wrapper.find('select').length).toBe(0);
  });

  describe('user check input[checkbox]', () => {
    wrapper = shallow(<RecursInput fields={fields} onInputChange={onInputChange} />, { context });
    const input = wrapper.find('input').first();

    beforeEach(() => {
      input.simulate('change');
    });

    it('should call onInputChange', () => {
      expect(onInputChange).toHaveBeenCalled();
    });
  });

  describe('showRecursInput prop is set to true', () => {
    beforeEach(() => {
      wrapper = shallow(
        <RecursInput
          fields={fields}
          onInputChange={onInputChange}
          showRecursInput
        />,
       { context });
    });

    it('should render 1 input', () => {
      expect(wrapper.find('input').length).toBe(1);
    });

    it('should render 1 select-element', () => {
      expect(wrapper.find('select').length).toBe(1);
    });

    describe('user change input', () => {
      const input = wrapper.find('input').first();
      beforeEach(() => {
        input.simulate('change');
      });

      it('should call onInputChange', () => {
        expect(onInputChange).toHaveBeenCalled();
      });
      describe('user change option', () => {
        const input = wrapper.find('select').first();
        beforeEach(() => {
          input.simulate('change');
        });

        it('should call onInputChange', () => {
          expect(onInputChange).toHaveBeenCalled();
        });
      });
    });
  });
});
