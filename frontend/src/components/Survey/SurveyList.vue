<template>
  <div class="dragula-container">
    <section class="drop-section">
      <p>공사중</p>
    </section>
    <section class="drag-section">
      <div class="section-containers">
        <div class="sub-button">
          <button class="sub-button-q">양식</button>
          <button class="sub-button-t">탬플릿</button>
        </div>
        <div class="sub-title">
          <p class="sub-title-text">드래그해서</p>
          <p class="sub-title-text">설문을 생성하세요.</p>
        </div>
        <div class="drag-parent">
          <div class="dragthing">
            <p class="q-type">객관식(단일선택)</p>
            <div class="question">
              <textarea
                class="question-title"
                type="text"
                placeholder="질문제목을 입력해주세요."
              />
              <button class="question-garbage">
                <i class="fas fa-trash-alt"></i>
              </button>
              <div class="q-option">
                <div class="q-option-item">
                  <div class="q-option-item-center">
                    <input type="radio" name="SINGLE" value="1" />
                  </div>
                  <input type="text" placeholder="선택1" />
                </div>
                <div class="q-option-item">
                  <div class="q-option-item-center">
                    <input type="radio" name="SINGLE" value="2" />
                  </div>
                  <input type="text" placeholder="선택2" />
                </div>
                <div class="q-option-item">
                  <div class="q-option-item-center">
                    <input type="radio" name="SINGLE" value="3" />
                  </div>
                  <input type="text" placeholder="선택3" />
                </div>
                <div class="question-control">
                  <p class="q-option-item-c1">'선택'</p>
                  <p>또는</p>
                  <p class="q-option-item-c2">'기타' 추가</p>
                </div>
              </div>
              <div class="is-required">
                <p class="is-required-text">필수 응답</p>
                <label class="toggle-control">
                  <input type="checkbox" checked="checked" />
                  <span class="control"></span>
                </label>
              </div>
              <div class="question-detail-btn">
                <p>세부문항 생성</p>
                <button><i class="fas fa-angle-double-down"></i></button>
              </div>
            </div>
          </div>
          <div class="dragthing">
            <p class="q-type">객관식(복수선택)</p>
            <div class="question">
              <textarea
                class="question-title"
                type="text"
                placeholder="질문제목을 입력해주세요."
              />
              <button class="question-garbage">
                <i class="fas fa-trash-alt"></i>
              </button>
              <div class="q-option">
                <div class="q-option-item">
                  <div class="q-option-item-center">
                    <input type="checkbox" name="MULTIPLE" value="1" />
                  </div>
                  <input type="text" placeholder="선택1" />
                </div>
                <div class="q-option-item">
                  <div class="q-option-item-center">
                    <input type="checkbox" name="MULTIPLE" value="2" />
                  </div>
                  <input type="text" placeholder="선택2" />
                </div>
                <div class="q-option-item">
                  <div class="q-option-item-center">
                    <input type="checkbox" name="MULTIPLE" value="3" />
                  </div>
                  <input type="text" placeholder="선택3" />
                </div>
                <div class="question-control">
                  <p class="q-option-item-c1">'선택'</p>
                  <p>또는</p>
                  <p class="q-option-item-c2">'기타' 추가</p>
                </div>
              </div>
              <div class="is-required">
                <p class="is-required-text">필수 응답</p>
                <label class="toggle-control">
                  <input type="checkbox" checked="checked" />
                  <span class="control"></span>
                </label>
              </div>
              <div class="question-detail-btn">
                <p>세부문항 생성</p>
                <button><i class="fas fa-angle-double-down"></i></button>
              </div>
            </div>
          </div>
          <div class="dragthing">
            <p class="q-type">주관식</p>
            <div class="question">
              <textarea
                class="question-title"
                type="text"
                placeholder="질문제목을 입력해주세요."
              />
              <button class="question-garbage">
                <i class="fas fa-trash-alt"></i>
              </button>
              <div class="q-option">
                <textarea
                  class="item-short"
                  type="text"
                  placeholder="주관식 텍스트"
                />
              </div>
              <div class="is-required">
                <p class="is-required-text">필수 응답</p>
                <label class="toggle-control">
                  <input type="checkbox" checked="checked" />
                  <span class="control"></span>
                </label>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      survey: {
        title: null,
        explain: null,
        is_anony: false, // 익명설문인지
        start_date: null, // surveyset 페이지에서 작성
        end_date: null, // surveyset 페이지에서 작성
        question: [],
        state: null, // surveyset 페이지에서 보낼 때 결정
        share: [], // surveyset에서
        target: [], // surveyset에서
        use_template: null,
        template: null,
      },
      question: {
        q_number: null, // 1, 1-1, 1-2, 2
        q_explantion: null,
        q_type: null, // SINGLE, MULTIPLE, SHORT
        is_required: true,
        q_option: [], // q_type이 SHORT 경우 q_option은 빈 배열
      },
      q_option: {
        o_number: null, // 문항의의 선택지 번호
        o_explanation: null,
        is_short: null, // 객관식의 o_number 선택지가 '기타'냐?
      },
    }
  },
}
</script>

<style scroped src="./../../css/survey/survey/survey-list.css"></style>
